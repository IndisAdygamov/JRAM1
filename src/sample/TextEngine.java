package sample;

public class TextEngine {
    private static final Character[] alphabet = {
                                            //'А','Б','В','Г','Д','Е','Ё','Ж','З','И',
                                            //'Й','К','Л','М','Н','О','П','Р','С','Т',
                                            //'У','Ф','Х','Ц','Ч','Ш','Щ','Ъ','Ы','Ь',
                                            //'Э','Ю','Я',
                                            'а','б','в','г','д','е','ё','ж','з','и',
                                            'й','к','л','м','н','о','п','р','с','т',
                                            'у','ф','х','ц','ч','ш','щ','ъ','ы','ь',
                                            'э','ю','я',
                                            '.',',','"',':','-','!','?',' '
                                            };
    public static Character[] getAlphabet() {
        return alphabet;
    }

    public static String encodeString(String sourceString, int key) {
        System.out.println("========== System log: Key = " + key + ", length of alphabet = " + alphabet.length + "==========");
        if(Math.abs(key) > alphabet.length) {
            key = key%alphabet.length;
        }
        char[] chars = sourceString.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < chars.length; i++) {
            stringBuilder.append( (TextEngine.controlWithUpperCharacter((chars[i]), key)) );
        }
        //System.out.println("EncodeString: \n" + sourceString);
        return stringBuilder.toString();
    }

    public static String decodeString(String sourceString, int key) {
        return encodeString(sourceString, -1*key);
    }

    public static String decodeStringAuto(String sourceString) {
        boolean testDots; // '.'
        boolean testCommas; // ','
        //boolean level3 = false;
        String dest = null;
        for(int i = 0; i < alphabet.length; i++) {
            dest = encodeString(sourceString, i);
            testDots = checkPoint(dest);
            testCommas = checkComma(dest);
            System.out.println("========== Status " + i + " 41: testDots " + testDots + ", testCommas " + testCommas + " ==========");
            System.out.println(dest);
            if(testDots && testCommas) {
                return dest;
            }
        }
        return null;
    }

    private static boolean checkPoint(String string) {
        char [] check = string.toCharArray();
        boolean statusSpace = false;
        boolean statusLowerOrDigits = false;
        for(int i = 1; i < check.length-1; i++) {
            if (check[i] == '.') {
                statusSpace = (check[i + 1] == ' ' || check[i] + 1 == '\n');
                if (Character.isLowerCase(check[i - 1]) || Character.isDigit(check[i - 1])) {
                    statusLowerOrDigits = true;
                } else statusLowerOrDigits = false;
            }
        }
        return statusSpace;
    }

    private static boolean checkComma(String string) {
        char [] check = string.toCharArray();
        boolean statusSpace = false;
        boolean statusLetterOrDigits = false;
        for(int i = 1; i < check.length-1; i++) {
            if (',' == check[i]) {
                statusSpace = check[i + 1] == ' ';
//                if (Character.isLetter(check[i - 1]) || Character.isDigit(check[i - 1])) {
//                    statusLetterOrDigits = true;
//                } else statusLetterOrDigits = false;
            }
        }
        return statusSpace;
    }

    private static Character controlWithUpperCharacter(char symbol, int shift) {
        if(Character.isLetter(symbol)) {
            if(Character.isUpperCase(symbol)) {
                symbol = Character.toUpperCase(returnCharacterWithShift(Character.toLowerCase(symbol), shift));
            }
            else {
                symbol = returnCharacterWithShift(symbol, shift);
            }
        }
        else {
            symbol = returnCharacterWithShift(symbol, shift);
        }
        return symbol;
    }
    private static Character returnCharacterWithShift(char symbol, int shift) {
        for(int i = 0; i < alphabet.length; i++) {
            if(alphabet[i] == symbol) {
                if(shift >= 0) {
                    return i + shift < alphabet.length ? alphabet[i + shift] : alphabet[(i + shift - alphabet.length)];
                }
                else {
                    return i + shift >= 0 ? alphabet[i + shift] : alphabet[(alphabet.length + i + shift)];
                }
            }
        }
        return symbol;
    }
}
