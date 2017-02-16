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

import datasource.FileDataSource;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SubstitutionDecryptorTest {

    @Test
    public void decryptWhenGivenAlphabet() throws FileNotFoundException {
        char[] alphabet = new char[]
                {'v', 'w', 'x', 'y', 'z', 'e', 'a', 'b', 'c',
                        'd', 'i', 'j', 'f', 'g', 'h', 'm', 'n',
                        'o', 'k', 'l', 'q', 'r', 's', 't', 'p', 'u'};
        SubstitutionDecryptor decryptor = new SubstitutionDecryptor(new FileDataSource("src/test/java/resources/ciphers/c4_memory_attack_and_DSA.txt"), alphabet);
        decryptor.decrypt();

        //Write plaintext and metrics to file
        OutputStream stream = new FileOutputStream(new File("output.txt"));
        decryptor.saveToStream(stream);

        Assert.assertEquals(new String(new FileDataSource("src/test/java/resources/plaintexts/c4_memory_attack_and_DSA.txt").getContents()), decryptor.getPlaintext());
    }

    @Test
    @Ignore("Not working right now, still need to work out the algorithm for getting the alphabet mapping")
    public void integration() {
        SubstitutionDecryptor decryptor = new SubstitutionDecryptor(new FileDataSource("src/test/java/resources/ciphers/c4_memory_attack_and_DSA.txt"));
        decryptor.decrypt();
    }
}
