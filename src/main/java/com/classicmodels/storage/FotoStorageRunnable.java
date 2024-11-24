package com.classicmodels.storage;

import org.springframework.web.multipart.MultipartFile;

public class FotoStorageRunnable implements Runnable{

    private final MultipartFile file;
//    private DeferredResult<ProductLinesInput> resultado;
    private final FotoStorage fotoStorage;
    private final String nome;

    public FotoStorageRunnable(MultipartFile file, FotoStorage fotoStorage, String nome) {
        this.file = file;
//        this.resultado = resultado;
        this.fotoStorage = fotoStorage;
        this.nome = nome;
    }

    public void run() {
        String nomeFoto = this.fotoStorage.salvar(file, nome);
        String contentType = file.getContentType();
//        resultado.setResult(new ProductLinesInput());
    }

}
