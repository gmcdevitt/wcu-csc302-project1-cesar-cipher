package decryptor;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;

public class CesarDecryptor extends CharacterDecryptor implements Decryptor {
    private Integer offset;

    CesarDecryptor(int offset, String cipher) {
        this.setOffset(offset);
        this.setCipher(cipher.toCharArray());
    }

    CesarDecryptor(int offset) {
        this.setOffset(offset);
    }

    CesarDecryptor(String cipher) {
        this.setCipher(cipher.toCharArray());
    }

    private void setOffset(int offset) {
        this.offset = offset;
    }

    public void decrypt() {
        if (cipher == null) {
            throw new RuntimeException("cipher was null, please set a ciphertext via setCipher(String) and try again.");
        }

        for (int i = 0; i < cipher.length; i++) {
            plaintext[i] = getCipherCharacterByOffset(cipher[i], offset);
        }
    }

    char getCipherCharacterByOffset(char cipherChar, Integer offset) {
        if (offset == null) {
            throw new RuntimeException("Offset is null!");
        }

        char plainTextChar;
        if (CharUtils.isAsciiAlpha(cipherChar)) { //Only do the offset check if its actually a letter
            plainTextChar = alphabet[Math.abs((ArrayUtils.indexOf(alphabet, Character.toLowerCase(cipherChar)) + offset)) % alphabet.length]; //Main logic to decrypt via cesar
            plainTextChar = CharUtils.isAsciiAlphaUpper(cipherChar) ? Character.toUpperCase(plainTextChar) : plainTextChar; //Retain case sensitivity
        } else {
            plainTextChar = cipherChar;
        }

        return plainTextChar;
    }
}

