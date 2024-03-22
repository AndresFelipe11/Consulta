package co.com.bancolombia.soapconsumer;


import org.bouncycastle.util.StringList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtils {
    public static final String NEWLINE = System.getProperty("line.separator");


    public static boolean hasContent(String str) {
        return str != null && str.trim().length() > 0;
    }


}
