package com.classicmodels.storage;

import com.classicmodels.api.model.input.ProductLinesInput;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class FotoStorageRunnable implements Runnable{

    private MultipartFile file;
//    private DeferredResult<ProductLinesInput> resultado;
    private FotoStorage fotoStorage;
    private String nome;

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
