package com.briannakayama.configure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CScript implements Configurable<Object> {

	public static void main(String[] args){
		CScript c = new CScript("script.yml");
		c.configure();
	}
	
	
	public static String TYPE_CONFIG = "configuration_types.txt";

	private static Map<String, Class<? extends Scriptable>> classes;
	static {
		classes = new HashMap<String, Class<? extends Scriptable>>();
		String[] type;
		try (FileReader fr = new FileReader(new File(TYPE_CONFIG));
				BufferedReader br = new BufferedReader(fr);) {
			String line;
			while ((line = br.readLine()) != null) {
				type = line.split(":", 2);
				try {	
					classes.put(type[0], Class.forName(type[1].trim()).asSubclass(Scriptable.class));
				} catch (ClassNotFoundException e) {
					throw new ConfigurationError("Unable to find class "
							+ type[1].trim(), e);
				} catch (Exception e) {
					throw new ConfigurationError("Incorrect type or formatting in "
							+ TYPE_CONFIG, e);
				}
			}
		} catch (IOException e) {
			throw new ConfigurationError("Unable to load " + TYPE_CONFIG, e);
		}
	}

	
	Map<String, CScript> scriptReferences = new HashMap<String, CScript>();
	Map<String, Configurable<?>> objectReferences = new HashMap<String, Configurable<?>>();
	Map<String, Configurable<?>> definitions = new HashMap<String, Configurable<?>>();
	TokenParser tp;

	
	
	enum state {
		BEGIN, GROUP, GROUP_OBJECT, LIST, OBJECT, PRIMITIVE, END
	};

	public CScript(String script) {
		try (TokenParser tp = new TokenParser(script)) {
			this.tp = tp;
			// Initialize parser
			boolean end = false;
			while (!end) {
				tp.next();
				if (tp.type == 'B'){
					tp.next();
					document();
					end = true;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public Object configure() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object configure(String reference) {
		if (objectReferences.containsKey(reference)){
			return objectReferences.get(reference);
		}
		throw new ConfigurationError("Unknown reference: '" + reference);
	}	

	private void document() {
		do {
			object_list();
			switch (tp.type) {
			case 'E':
				return;
			case 'B':
				tp.next();
				definitions = new HashMap<String, Configurable<?>>();
				break;
			default:
				throw new ConfigurationError("Incorrect document symbol '" + tp.type
						+ "', expected (---|...)");
			}
		} while (true);
	}

	private void object_list() {
		do {
			if (tp.type == '&') {
				tp.next();
				declaration(true);
			} else {
				throw new ConfigurationError("Incorrect object symbol '" + tp.type
						+ "', expected (&)");
			}
			if (tp.type == ',' ){
				tp.next();
			} else {
				return;
			}
		} while (true);
	}

	private Configurable<?> declaration(boolean object_list) {
		if (tp.type == 'n'){
			String name = tp.nData;
			tp.next();
			Configurable<?> expression = expression();
			if (object_list) {
				objectReferences.put(name, expression);
			}
			definitions.put(name, expression);
			return expression;
		} else {
			throw new ConfigurationError(tp.lineNo, tp.script, "Expected name for declaration");
		}
	}

	private Configurable<?> expression() {
		switch (tp.type) {
		case '[':
			tp.next();
			return list();
		case '{':
			tp.next();
			return map();
		case '!':
			tp.next();
			return class_();
		case '&':
			tp.next();
			return declaration(false);
		case '*':
			tp.next();
			return reference();
		default:
			return primitive();
		}
	}
	
	private Configurable<?> reference(){
		if (tp.type == 'n'){
			if (definitions.containsKey(tp.nData)){
				Configurable<?> data = definitions.get(tp.nData);
				tp.next();
				if (tp.type == '{'){
					CObject c = CObject.class.cast(data);
					Map<String, Configurable<?>> variables;
					tp.next();
					variables = variables();
					return c.copyOveride(variables);
				}
				return data;
			} else {
				throw new ConfigurationError("No declaration for reference " + tp.nData);
			}
		} else {
			throw new ConfigurationError("Expected a name for the reference");
		}
	}
	
	private CObject class_(){
		if (tp.type == 'n'){
			Class<? extends Scriptable> c;
			if (classes.containsKey(tp.nData)){
				c = classes.get(tp.nData);
			} else {
				try {	
					c = Class.forName(tp.nData).asSubclass(Scriptable.class);
				} catch (ClassNotFoundException e) {
					throw new ConfigurationError("Unable to find class "
							+ tp.nData, e);
				} catch (Exception e) {
					throw new ConfigurationError("Incorrect type or formatting in "
							+ TYPE_CONFIG, e);
				}
			}
			tp.next();
			Map<String, Configurable<?>> variables;
			if (tp.type == '{'){
				tp.next();
				variables = variables();
			}
			else {
				variables = new HashMap<String, Configurable<?>>();
			}
			return new CObject(c, variables);
		} else {
			throw new ConfigurationError("Expected a name for the class");
		}
	}
	
	private CPrimitive<?> primitive() {
		CPrimitive<?> data;
		switch (tp.type){
		case 'b':
			data = new CPrimitive<Boolean>(tp.bData);	
			break;
		case 'd':
			data = new CPrimitive<Double>(tp.dData);	
			break;
		case 'i':
			data = new CPrimitive<Integer>(tp.iData);	
			break;
		case 's':
			data = new CPrimitive<String>(tp.sData);	
			break;
		default:
			throw new ConfigurationError("Expected a primitive.");
		}
		tp.next();
		return data;
	}
	
	private CMap map(){
		Map<CPrimitive<?>,Configurable<?>> map= new HashMap<CPrimitive<?>,Configurable<?>>();
		do {
			if (tp.type != '}') {
				CPrimitive<?> p = primitive();
				if (tp.type == ':'){
					tp.next();
					Configurable<?> c = expression();
					map.put(p, c);
				} else {
					throw new ConfigurationError("Incorrect map symbol '" + tp.type
							+ "', expected (:)");
				}
				if (tp.type == ','){
					tp.next();
				} else if (tp.type != '}') {
					throw new ConfigurationError("Incorrect map symbol '" + tp.type
							+ "', expected (})");
				}
			} else {
				tp.next();
				return new CMap(map);
			}
		} while (true);
		
	}
	
	private CList list(){
		List<Configurable<?>> list= new ArrayList<Configurable<?>>();
			do {
				if (tp.type != ']') {
					list.add(expression());

					if (tp.type == ','){
						tp.next();
					} else if (tp.type != ']') {
						throw new ConfigurationError("Incorrect list symbol '" + tp.type
								+ "', expected (])");
					}
				} else {
					tp.next();
					return new CList(list);
				}
			} while (true);
	}
	
	private Map<String, Configurable<?>> variables(){
		Map<String,Configurable<?>> map= new HashMap<String, Configurable<?>>();
		do {
			if (tp.type == 'n') {

				String s = tp.nData;
				if (tp.type == ':'){
					tp.next();
					Configurable<?> c = expression();
					map.put(s, c);
				} else {
					throw new ConfigurationError("Incorrect variable symbol '" + tp.type
							+ "', expected (:)");
				}
				if (tp.type == ','){
					tp.next();
				} else if (tp.type != '}') {
					throw new ConfigurationError("Incorrect variable symbol '" + tp.type
							+ "', expected (})");
				}
			} else if (tp.type == '}'){
				tp.next();
				return map;
			} else {
				throw new ConfigurationError("Incorrect map symbol '" + tp.type
						+ "', expected (:)");
			}	
		} while (true);
	}
}
