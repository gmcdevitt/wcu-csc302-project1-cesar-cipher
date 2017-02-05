package decryptor;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;

public class CesarDecryptor implements Decryptor {
    private Integer offset;
    private char[] plaintext;
    private char[] cipher;


    private char[] alphabet =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                    's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};


    CesarDecryptor(int offset) {
        this.offset = offset;
    }

    public Decryptor setCipher(String cipher) {
        this.cipher = cipher.toCharArray();
        this.plaintext = new char[cipher.length()];
        return this;
    }

    public void decrypt() {
        if (cipher == null) {
            throw new RuntimeException("cipher was null, please set a ciphertext via setCipher(String) and try again.");
        }

        for (int i = 0; i < cipher.length; i++) {
            plaintext[i] = getCipherCharacterByOffset(cipher[i], offset);
        }
    }

    private char getCipherCharacterByOffset(char cipherChar, Integer offset) {
        char plainTextChar;
        if (CharUtils.isAsciiAlpha(cipherChar)) { //Only do the offset check if its actually a letter
            plainTextChar = alphabet[Math.abs((ArrayUtils.indexOf(alphabet, Character.toLowerCase(cipherChar)) + offset)) % 26]; //Main logic to decrypt
            plainTextChar = CharUtils.isAsciiAlphaUpper(cipherChar) ? Character.toUpperCase(plainTextChar) : plainTextChar; //Retain case sensitivity
        } else {
            plainTextChar = cipherChar;
        }

        return plainTextChar;
    }

    public String getPlaintext() {
        return new String(plaintext);
    }
}

