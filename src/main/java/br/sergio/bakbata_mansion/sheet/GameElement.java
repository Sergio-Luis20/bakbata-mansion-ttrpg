package br.sergio.bakbata_mansion.sheet;

public interface GameElement<T> {

    default T getId() {
        return null;
    }

    String getName();

    String getDescription();

}
