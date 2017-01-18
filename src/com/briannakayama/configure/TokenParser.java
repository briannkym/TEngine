package com.briannakayama.configure;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenParser implements Closeable {
	
	//(indent)(begin)(end)(symbol)(boolean)(name)(double)(int)(string)
	private static Pattern token = Pattern
			.compile("( *)(\\.\\.\\.)|(---)|([-&!*,\\[\\{\\]\\}:#])"
					+ "|(true(?!\\w)|false(?!\\w)|t(?!\\w)|f(?!\\w))|([A-Za-z]\\w*)|(\\d+[.]\\d+)|(\\d+)"
					+ "|(?:\"((?:\\\\\"|[^\"])*)\")");

	private static Pattern escape = Pattern
			.compile("\\\\.");

	
	
	private BufferedReader br;
	private Matcher m;
	String script;
	int indent = 0;
	char type = ' ';
	String nData = null;
	String sData = null;
	int iData = 0;
	double dData = 0;
	boolean bData = false;
	int lineNo = 0;

	public static void main(String[] args){
		try(TokenParser t = new TokenParser("script.yml")){
			while(true){
				t.next();
				System.out.println(t.type + "," + t.nData + "," + t.sData + "," + t.iData + "," + t.dData + "," + t.bData);
			}
		} catch(Exception e){
			
		}
	}
	
	
	public TokenParser(String script) throws IOException {
		this.script = script;
		FileReader fr = new FileReader(new File(script));
		br = new BufferedReader(fr);
		m = token.matcher("");
	}

	public void next() {
		if (m.find()) {
			System.out.print(m.group() + " ");
			//(indent)(begin)(end)(symbol)(boolean)(name)(double)(int)(string)
			indent = (m.group(1) != null) ? m.group(1).length() : 0;
			if (m.group(2) != null){
				type = 'E';
			} else if (m.group(3) != null){
				type = 'B';
			} else if (m.group(4) != null){
				type = m.group(4).charAt(0);
			} else if (m.group(5) != null){
				type = 'b';
				bData = Boolean.parseBoolean(m.group(5));
			} else if (m.group(6) != null){
				type = 'n';
				nData = m.group(6);
			} else if (m.group(7) != null){
				type = 'd';
				dData = Double.parseDouble(m.group(7));
			} else if (m.group(8) != null){
				type = 'i';
				iData = Integer.parseInt(m.group(8));
			} else if (m.group(9) != null){
				type = 's';
				Matcher e = escape.matcher(m.group(9));
				int end = 0;
				StringBuffer sb = new StringBuffer();
				while (e.find()){
					end = e.end();
					char c = e.group().charAt(1);
					switch(c){
					case 't':
						e.appendReplacement(sb, "\t");
						break;
					case 'n':
						e.appendReplacement(sb, "\n");
						break;
					case '\\':
						e.appendReplacement(sb, "\\\\");
						break;
					default:
						e.appendReplacement(sb,"" + c);
					}
				}
				sb.append(m.group(9).substring(end));
				sData = sb.toString();
			}

			return;
		} else {
			try {
				System.out.println();
				String line = br.readLine();
				lineNo++;
				type = '\n';
				
				if (line != null) {
					m = token.matcher(line);
					return;
				}
			} catch (IOException e) {
				throw new ConfigurationError("Unable to load " + script + ":",
						e);
			}
		}
		throw new ConfigurationError("File " + script + " ended unexpectedly.");
	}

	@Override
	public void close() throws IOException {
		br.close();
	}
}
