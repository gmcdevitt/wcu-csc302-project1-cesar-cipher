package decryptor;

import datasource.FileDataSource;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SubstitutionDecryptorTest {

    @Test
    public void decryptWhenGivenAlphabet() {
        char[] alphabet = new char[]
                {'v', 'w', 'x', 'y', 'z', 'e', 'a', 'b', 'c',
                        'd', 'i', 'j', 'f', 'g', 'h', 'm', 'n',
                        'o', 'k', 'l', 'q', 'r', 's', 't', 'p', 'u'};
        SubstitutionDecryptor decryptor = new SubstitutionDecryptor(new FileDataSource("src/test/java/resources/ciphers/c4_memory_attack_and_DSA.txt"), alphabet);
        decryptor.decrypt();

        Assert.assertEquals(new String(new FileDataSource("src/test/java/resources/plaintexts/c4_memory_attack_and_DSA.txt").getContents()), decryptor.getPlaintext());
    }

    @Test
    @Ignore("Still need to work out the algorithm for getting the alphabet mapping")
    public void integration() {
        SubstitutionDecryptor decryptor = new SubstitutionDecryptor(new FileDataSource("src/test/java/resources/ciphers/c4_memory_attack_and_DSA.txt"));
        decryptor.decrypt();
        System.out.println(decryptor.getPlaintext());
    }
}
