package org.apache.drill.contrib.function;

import java.security.*;

import javax.inject.Inject;

import org.apache.drill.exec.expr.DrillSimpleFunc;
import org.apache.drill.exec.expr.annotations.FunctionTemplate;
import org.apache.drill.exec.expr.annotations.Output;
import org.apache.drill.exec.expr.annotations.Param;
import org.apache.drill.exec.expr.holders.NullableVarCharHolder;
import org.apache.drill.exec.expr.holders.VarCharHolder;

import io.netty.buffer.DrillBuf;

@FunctionTemplate(
        name = "sha1",
        scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL
)
public class SHA1Func implements DrillSimpleFunc  {

	@Param
    NullableVarCharHolder input;

    @Output
    VarCharHolder out;

    @Inject
    DrillBuf buffer;
    
	public void setup() {
	}

	public void eval() {
        String stringValue = org.apache.drill.exec.expr.fn.impl.StringFunctionHelpers.toStringFromUTF8(input.start, input.end, input.buffer);

        String outputValue = "";
		try {
			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(stringValue.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			outputValue = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
		
        // put the output value in the out buffer
        out.buffer = buffer;
        out.start = 0;
        out.end = outputValue.getBytes().length;
        buffer.setBytes(0, outputValue.getBytes());
	}

//	private static final String encSha1(String input) {
//		try {
//			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
//			byte[] result = mDigest.digest(input.getBytes());
//			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < result.length; i++) {
//				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
//			}
//			
//			return sb.toString();
//	    } catch (NoSuchAlgorithmException e) {
//	        e.printStackTrace();
//	    }
//	    return "";
//    }
	
//     public static void main(String args[]) {
//    	 String stringValue = "abcde";
//         System.out.println(encSha1(stringValue));
//     }
}
