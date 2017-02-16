package decryptor;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;

import java.text.DecimalFormat;

abstract class CharacterDecryptor {
    char[] plaintext;
    char[] cipher;

    //Now I know my abc's
    char[] alphabet =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                    's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * Taken from https://en.wikipedia.org/wiki/Letter_frequency
     * General frequency of each letter in the english alphabet
     */
    double[] letterFrequencies =
            {8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153,
                    0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987,
                    6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074};

    void setCipher(char[] cipher) {
        this.cipher = cipher;
        this.plaintext = new char[cipher.length];
    }

    String getPlaintext() {
        return new String(plaintext);
    }

    String getCipherAsString() {
        return new String(cipher);
    }

    /**
     * Returns the frequency of each letter in the cipher text, can be used to make an educated guess on which letters are which
     * @return a double[26] of the frequency of each letter in the ciphertext
     */
    double[] getLetterFrequency() {
        double[] frequency = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int length = 0; //we can't rely on cipher.length value because of special characters

        //Go through each letter in the cipher and add to the array
        for (char letter : cipher) {
            if (CharUtils.isAsciiAlpha(letter)) {
                frequency[ArrayUtils.indexOf(alphabet, Character.toLowerCase(letter))]++;
                length++;
            }
        }

        //Divide the count by the number of characters
        for (int i = 0; i < frequency.length; i++) {
            frequency[i] = Double.valueOf(new DecimalFormat("#.###").format((frequency[i] / length) * 100));
        }

        return frequency;
    }

    int findClosestMatchInArray(double[] searchArray, double criteria) {
        double distance = Math.abs(searchArray[0] - criteria);
        int idx = 0;
        for(int c = 1; c < searchArray.length; c++){
            double cdistance = Math.abs(searchArray[c] - criteria);
            if((cdistance < distance) && (searchArray[c] != -1)) {
                idx = c;
                distance = cdistance;
            }
        }
        searchArray[idx] = -1;
        return idx;
    }

    @Override
    public String toString() {
        double[] frequency = getLetterFrequency();
        StringBuilder metrics = new StringBuilder();
        metrics.append("Metrics are in format: ('letter', 'percent occurcance')\n");
        for (int i = 0; i < 26; i++) {
            metrics.append(String.format("('%s','%s')\n", alphabet[i], frequency[i]));
        }
        return metrics.toString();
    }

}
