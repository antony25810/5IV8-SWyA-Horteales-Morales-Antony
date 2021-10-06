/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cifrado.des;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 *
 * @author MARIA DEL REFUGIO
 */
public class Funciones {
    public void Ciframiento(String clave, String archivo) throws NoSuchAlgorithmException, NoSuchPaddingException, FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException{
        Cipher cifrado = Cipher.getInstance("DES/ECB/PKCS5Padding");
        SecretKeyFactory skf=SecretKeyFactory.getInstance("DES");
        DESKeySpec kspec = new DESKeySpec(clave.getBytes());
        SecretKey subllave = skf.generateSecret(kspec);
        cifrado.init(Cipher.ENCRYPT_MODE, subllave);
        byte[] buffer = new byte[1000]; //quiero ir leyendo de 1000 en 1000 bits del fichero
        byte[] bufferCifrado; //aqui voy a almacenar el resultado
        FileInputStream in = new FileInputStream(archivo);
        FileOutputStream out = new FileOutputStream(archivo + ".cifrado.txt");
        int bytesleidos = in.read(buffer, 0, 1000);
        while(bytesleidos != -1){
            //mientras no se llegue al final del archivo o fichero
            bufferCifrado = cifrado.update(buffer, 0, bytesleidos);
            //para el texto claro leer y enviarlo al buffer cifrado
            out.write(bufferCifrado);
            //escribir el archivo cifrado
            bytesleidos = in.read(buffer, 0, 1000);
        }
        //acompletar el fichero o archivo con el cifrado
        bufferCifrado = cifrado.doFinal();
        out.write(bufferCifrado); //escribir el final del texto cifrado si lo hay
        
        in.close();
        out.close();
    }
    public void Desciframiento(String clave, String archivo) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException{
        Cipher cifrado = Cipher.getInstance("DES/ECB/PKCS5Padding");
        SecretKeyFactory skf=SecretKeyFactory.getInstance("DES");
        DESKeySpec kspec = new DESKeySpec(clave.getBytes());
        SecretKey subllave = skf.generateSecret(kspec);
        cifrado.init(Cipher.DECRYPT_MODE, subllave);
        byte[] buffer = new byte[1000]; //quiero ir leyendo de 1000 en 1000 bits del fichero
        byte[] bufferPlano; //aqui voy a almacenar el resultado
        FileInputStream in = new FileInputStream(archivo);
        FileOutputStream out = new FileOutputStream(archivo + ".descifrado.txt");
        int bytesleidos = in.read(buffer, 0, 1000);
        while(bytesleidos != -1){
            //mientras no se llegue al final del archivo o fichero
            bufferPlano = cifrado.update(buffer, 0, bytesleidos);
            //para el texto claro leer y enviarlo al buffer cifrado
            out.write(bufferPlano);
            //escribir el archivo cifrado
            bytesleidos = in.read(buffer, 0, 1000);
        }
        //acompletar el fichero o archivo con el cifrado
        bufferPlano = cifrado.doFinal();
        out.write(bufferPlano); //escribir el final del texto cifrado si lo hay
        
        in.close();
        out.close();
    }
}
