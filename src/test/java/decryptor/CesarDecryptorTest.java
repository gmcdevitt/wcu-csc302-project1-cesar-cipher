package decryptor;

import decryptor.resources.Stubs;
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
                {5.88, 8.82, 0.0, 0.0, 5.88, 0.0, 0.0, 8.82, 0.0, 0.0, 2.94,
                        0.0, 0.0, 8.82, 0.0, 0.0, 5.88, 8.82, 8.82, 14.71,
                        0.0, 5.88, 0.0, 8.82, 5.88, 0.0};

        CesarDecryptor decryptor = new CesarDecryptor(cipher);

        Assert.assertArrayEquals(expected, decryptor.getLetterFrequency(), 0);
    }

    /**
     * A smart Cesar Decryptor is able to effectively guess the offset using smart analysis with letter frequency and patterns.
     *
     * I suppose we can always best-guess the solution first and then just brute force the other 25 possibilities... just to give the best shot
     *
     * the problem with guessing offset is that the text has to be sufficiently large for the occurrence percentages to be normalized.
     *
     * for guessing the offset, we should go off of the most occurring letter.
     */
    @Test
    public void guessOffset() {
        int expectedOffset = 5;
        CesarDecryptor decryptor = new CesarDecryptor(Stubs.cipherText);

        Assert.assertEquals("The guessed offset was not 5!", expectedOffset, decryptor.guessOffset());
    }
}
