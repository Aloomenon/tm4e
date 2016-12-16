package org.eclipse.tm4e.core.internal.grammar.parser.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.eclipse.tm4e.core.internal.grammar.parser.PList;
import org.eclipse.tm4e.core.internal.grammar.reader.IGrammarParser;
import org.eclipse.tm4e.core.internal.types.IRawGrammar;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class JSONPListParser implements IGrammarParser {

	public static final IGrammarParser INSTANCE = new JSONPListParser();

	@Override
	public IRawGrammar parse(InputStream contents) throws Exception {
		PList pList = new PList();
		JsonReader reader = new JsonReader(new InputStreamReader(contents, Charset.forName("UTF-8")));
		// reader.setLenient(true);
		boolean parsing = true;
		while (parsing) {
			JsonToken nextToken = reader.peek();
			switch (nextToken) {
			case BEGIN_ARRAY:
				pList.startElement(null, "array", null, null);
				reader.beginArray();
				break;
			case END_ARRAY:
				pList.endElement(null, "array", null);
				reader.endArray();
				break;
			case BEGIN_OBJECT:
				pList.startElement(null, "dict", null, null);
				reader.beginObject();
				break;
			case END_OBJECT:
				pList.endElement(null, "dict", null);
				reader.endObject();
				break;
			case NAME:
				String lastName = reader.nextName();
				pList.startElement(null, "key", null, null);
				pList.characters(lastName.toCharArray(), 0, lastName.length());
				pList.endElement(null, "key", null);
				break;
			case NULL:
				reader.nextNull();
				break;
			case BOOLEAN:
				reader.nextBoolean();
				break;
			case NUMBER:
				reader.nextLong();
				break;
			case STRING:
				String value = reader.nextString();
				pList.startElement(null, "string", null, null);
				pList.characters(value.toCharArray(), 0, value.length());
				pList.endElement(null, "string", null);
				break;
			case END_DOCUMENT:
				parsing = false;
				break;
			default:
				break;
			}
		}
		reader.close();
		return pList.getResult();
	}

}
