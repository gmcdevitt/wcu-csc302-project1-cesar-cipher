package decryptor;

import org.junit.Assert;
import org.junit.Test;

public class DecryptorTest {

    /**
     * Any good Cesar Cipher decryptor can use a given offset to decrypt ciphertext.
     *
     * Make sure I can decrypt something when given the offset
     */
    @Test
    public void testDecryptWithOffset() {
        int offset = 13;
        String plaintext = "Hello World";
        String cipher = "Uryyb Jbeyq"; //Encrypted with offset 13

        Decryptor decryptor = new CesarDecryptor(offset)
                .setCipher(cipher);

        decryptor.decrypt();

        Assert.assertEquals("Oh no! The decrypted message was not 'Hello World'", plaintext, decryptor.getPlaintext());
    }
}
