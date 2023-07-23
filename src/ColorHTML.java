import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ColorHTML {
    public String colorPreview(String testString){
        // Loading Font Code
        String fontCSS = loadFontCSS();

        // Hash Map, sorry, idk how else to do it LMAO
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("\\u0001", "B8B8E8");
        colorMap.put("\\u0002", "B8B8E8");
        colorMap.put("\\u0003", "DCDC3C");
        colorMap.put("\\u0004", "FFFFFF");
        colorMap.put("\\u0005", "847474");
        colorMap.put("\\u0006", "C81818");
        colorMap.put("\\u0007", "10FC18");
        colorMap.put("\\u0008", "F40404");
        colorMap.put("\\u0009", "B8B8E8");
        colorMap.put("\\u000A", "B8B8E8");
        colorMap.put("\\u000B", "B8B8E8");
        colorMap.put("\\u000C", "B8B8E8");
        colorMap.put("\\u000D", "B8B8E8");
        colorMap.put("\\u000E", "0C48CC");
        colorMap.put("\\u000F", "2CB494");
        colorMap.put("\\u0010", "88409C");
        colorMap.put("\\u0011", "F88C14");
        colorMap.put("\\u0012", "B8B8E8");
        colorMap.put("\\u0013", "B8B8E8");
        colorMap.put("\\u0014", "B8B8E8");
        colorMap.put("\\u0015", "703014");
        colorMap.put("\\u0016", "CCE0D0");
        colorMap.put("\\u0017", "FCFC38");
        colorMap.put("\\u0018", "088008");
        colorMap.put("\\u0019", "FCFC7C");
        colorMap.put("\\u001A", "B8B8E8");
        colorMap.put("\\u001B", "ECC4B0");
        colorMap.put("\\u001C", "4068D4");
        colorMap.put("\\u001D", "74A47C");
        colorMap.put("\\u001E", "9090B8");
        colorMap.put("\\u001F", "00E4FC");

        String currCode = "";
        String prevCode = "";
        String HTMLString = "<html><body bgcolor='#000000'><style>" + fontCSS + "</style>";
        String HTMLColor = "";
        for (int i = 0; i < testString.length(); i++){
            if(String.valueOf(testString.charAt(i)).equals("\\")){
                if(String.valueOf(testString.charAt(i)).equals("\\") && String.valueOf(testString.charAt(i+1)).equals("n")){
                    HTMLString += "<br></br>";
                    i += 2;
                } else {
                    if (currCode.equals("")){
                        currCode = testString.substring(i, i+6);
                    } else if(testString.substring(i, i+6).equals("\\u0001")){
                        currCode = prevCode;
                    } else {
                        prevCode = currCode;
                        currCode = testString.substring(i, i+6);
                    }
                    i += 6;
                }
            }
            HTMLColor = colorMap.getOrDefault(currCode, "B8B8E8");
            HTMLString += "<div style='display: inline; color:#" + HTMLColor + "'>" + testString.charAt(i) + "</div>";
        }
        HTMLString += "</body></html>";
        return HTMLString;
    }

    public String loadFontCSS() {
        try {
            // Load the custom font CSS from the font file
            InputStream fontStream = getClass().getResourceAsStream("/fonts/Kostar.ttf");
            byte[] fontBytes = IOUtils.toByteArray(fontStream);
            String fontBase64 = Base64.getEncoder().encodeToString(fontBytes);

            // Generate the CSS to apply the custom font
            return "@font-face { font-family: 'kostar'; src: url(data:font/ttf;charset=utf-8;base64," + fontBase64 + "); }"
                    + "body { font-family: 'kostar'; font-size: 16px; }";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
