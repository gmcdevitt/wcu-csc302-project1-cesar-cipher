/*
 *     codes.gerard.cryptography, a simple, expandable app dealing with cryptography
 *     Copyright (C) 2017 Gerard McDevitt
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package decryptor;

import datasource.DataSource;
import org.apache.commons.lang3.CharUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SubstitutionDecryptor extends CharacterDecryptor implements Decryptor {
    private char[] determinedAlphabet;
    private Map<Character, Character> letterMapping;

    SubstitutionDecryptor(DataSource dataSource) {
        this.setCipher(dataSource.getContents());
    }

    public SubstitutionDecryptor(DataSource dataSource, char[] alphabet) {
        this.setCipher(dataSource.getContents());
        this.determinedAlphabet = alphabet;
    }

    public void decrypt() {
        Map<Character, Character> letterMapping = getLetterMapping(getLetterFrequency());
        decrypt(letterMapping);
    }

    private Map<Character, Character> getLetterMapping(double[] letterFrequency) {
        Map<Character, Character> letterMap = new HashMap<Character, Character>();
        if (determinedAlphabet == null) {
            //Do it the hard way
            Map<Integer, Integer> orderMap = new HashMap<Integer, Integer>();
            double[] old = new double[26];
            System.arraycopy(letterFrequency, 0, old, 0, 26);
            Arrays.sort(letterFrequency);


            for (int i = 0; i < 26; i++) {
                orderMap.put(Arrays.binarySearch(letterFrequency, old[i]), i);
            }

            for (int i = 0; i < 26; i++) {
                int indexOfLetter = super.findClosestMatchInArray(super.letterFrequencies, letterFrequency[i]);
                letterMap.put(alphabet[i], alphabet[orderMap.get(indexOfLetter)]);
            }
        } else {
            //Wow this is easy
            //Build the map
            for (int i = 0; i < 26; i++) {
                letterMap.put(alphabet[i], determinedAlphabet[i]);
            }
        }
        this.letterMapping = letterMap;
        return letterMap;
    }

    private void decrypt(Map<Character, Character> mapping) {
        boolean isUpper;
        for (int i = 0; i < cipher.length; i++) {
            isUpper = Character.isUpperCase(cipher[i]);
            if (CharUtils.isAsciiAlpha(cipher[i])) {
                plaintext[i] = isUpper ? Character.toUpperCase(mapping.get(Character.toLowerCase(cipher[i]))) : mapping.get(Character.toLowerCase(cipher[i]));
            } else {
                plaintext[i] = cipher[i];
            }
        }
    }

    public void saveToStream(OutputStream stream) {
        try {
            stream.write("---BEGIN PLAINTEXT DOCUMENT---\n".getBytes(StandardCharsets.UTF_8));
            stream.write(getPlaintext().getBytes(StandardCharsets.UTF_8));
            stream.write("---END PLAINTEXT DOCUMENT---\n".getBytes(StandardCharsets.UTF_8));
            stream.write("\n".getBytes(StandardCharsets.UTF_8));
            stream.write(this.toString().getBytes(StandardCharsets.UTF_8));
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException("Writing to stream failed", e);
        }
    }

    @Override
    public String toString() {
        StringBuilder metrics = new StringBuilder();
        metrics.append(super.toString());

        if (letterMapping == null) {
            getLetterMapping(getLetterFrequency());
        }

        metrics.append("\nMetrics are in format: ciphertext character = plaintext character.\n");
        return metrics.append(letterMapping.toString()).append("\n").toString();
    }

}
