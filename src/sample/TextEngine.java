package sample;

public class TextEngine {
    private static Character[] alphabet = {
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
        System.out.println("========== System log: Key = " + key + ", length of alphabet = " + alphabet.length);
        if(Math.abs(key) > alphabet.length) {
            key = key%alphabet.length;
        }
        char[] chars = sourceString.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < chars.length; i++) {
            stringBuilder.append( (TextEngine.controlWithUpperCharacter((chars[i]), key)) );
        }
        System.out.println("EncodeString: " + sourceString.toString());
        return stringBuilder.toString();
    }

    public static String decodeString(String sourceString, int key) {
        return encodeString(sourceString, -1*key);
    }

    public static String decodeAuto(String sourceString) {
        String destinationString = null;
        return destinationString;
    }

    private static Character controlWithUpperCharacter(char symbol, int shift) {
        boolean upperSymbol = false;
        System.out.println("Char before: " + symbol);
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
        System.out.println("Char after: "+symbol);
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
