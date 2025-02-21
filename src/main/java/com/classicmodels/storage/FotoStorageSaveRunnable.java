package com.classicmodels.storage;

public class FotoStorageSaveRunnable implements Runnable{

    private final byte[] file;

    private final FotoStorage fotoStorage;
    private final String contentType;
    private final String nome;

    public FotoStorageSaveRunnable(byte[] file, FotoStorage fotoStorage, String contentType, String nome) {
        this.file = file;
        this.fotoStorage = fotoStorage;
        this.contentType = contentType;
        this.nome = nome;
    }

    public void run() {
        fotoStorage.salvar(file, contentType, nome);
    }

}
