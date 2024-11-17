package com.classicmodels.storage;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FotoStorage {

    public String salvar(MultipartFile file, String nome);

//    public byte[] recuperar(String foto);
//
//    public void excluir(String foto);
//
//    public String getUrl(String foto);
//
//    default String renomearArquivo(String nomeOriginal) {
//        return UUID.randomUUID().toString() + "_" + nomeOriginal;
//    }

}
