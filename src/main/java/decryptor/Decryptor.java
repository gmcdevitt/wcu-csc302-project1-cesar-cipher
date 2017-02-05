package decryptor;

public interface Decryptor {
    void decrypt();

    Decryptor setCipher(String cipher);

    String getPlaintext();

}
