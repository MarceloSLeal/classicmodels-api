package com.classicmodels.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

    public String salvar(byte[] file, String contentType, String nome);

    public byte[] recuperar(String foto);

    public String excluir(String foto);

//    public String getUrl(String foto);
//
//    default String renomearArquivo(String nomeOriginal) {
//        return UUID.randomUUID().toString() + "_" + nomeOriginal;
//    }

}
