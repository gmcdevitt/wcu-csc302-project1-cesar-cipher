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

import org.junit.Assert;
import org.junit.Test;

public class CesarDecryptorTest {

    /**
     * Make sure i can get the correct plain character from the alphabet when given the offset.
     */
    @Test
    public void getCipherCharacterByOffset() {
        int offset = 13;
        char plaintext = 'H';
        char cipher = 'U';

        CesarDecryptor decryptor = new CesarDecryptor(offset);

        Assert.assertEquals("Oh no! The decrypted character was not 'H'",
                plaintext, decryptor.getCipherCharacterByOffset(cipher, offset));
    }

    /**
     * Any good Cesar Cipher decryptor can use a given offset to decrypt ciphertext.
     *
     * Make sure I can decrypt something when given the offset, also tests that I keep special characters as-is
     */
    @Test
    public void testDecryptWithOffset() {
        int offset = 13;
        String plaintext = "Hello World!";
        String cipher = "Uryyb Jbeyq!"; //Encrypted with offset 13

        CesarDecryptor decryptor = new CesarDecryptor(offset, cipher);
        decryptor.decrypt();

        Assert.assertEquals("Oh no! The decrypted message was not 'Hello World'", plaintext, decryptor.getPlaintext());
    }

    /**
     * Read through the ciphertext for the frequency of each letter.
     */
    @Test
    public void getLetterFrequency() {
        String cipher = "aaqqttxxvvbbennbnssxsetrrrttykyhhh";
        double[] expected = new double[]
                {5.882, 8.824, 0.0, 0.0, 5.882, 0.0, 0.0, 8.824, 0.0, 0.0, 2.941,
                        0.0, 0.0, 8.824, 0.0, 0.0, 5.882, 8.824, 8.824, 14.706,
                        0.0, 5.882, 0.0, 8.824, 5.882, 0.0};

        CesarDecryptor decryptor = new CesarDecryptor(cipher);

        Assert.assertArrayEquals(expected, decryptor.getLetterFrequency(), 0);
    }
}
