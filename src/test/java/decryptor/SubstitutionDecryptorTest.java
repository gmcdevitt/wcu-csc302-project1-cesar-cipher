package decryptor;

import datasource.FileDataSource;
import org.junit.Ignore;
import org.junit.Test;

public class SubstitutionDecryptorTest {

    @Test
    @Ignore("Still need to work out the algorithm for getting the alphabet mapping")
    public void integration() {
        SubstitutionDecryptor decryptor = new SubstitutionDecryptor(new FileDataSource("src/test/java/resources/ciphers/c4_memory_attack_and_DSA.txt"));
        decryptor.decrypt();
        System.out.println(decryptor.getPlaintext());
    }
}
