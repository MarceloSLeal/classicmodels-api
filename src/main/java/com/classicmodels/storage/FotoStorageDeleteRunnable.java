package com.classicmodels.storage;

public class FotoStorageDeleteRunnable implements Runnable {

    private final String foto;
    private final FotoStorage fotoStorage;

    public FotoStorageDeleteRunnable(String foto, FotoStorage fotoStorage) {
        this.foto = foto;
        this.fotoStorage = fotoStorage;
    }

    public void run() {
        fotoStorage.excluir(foto);
    }

}
