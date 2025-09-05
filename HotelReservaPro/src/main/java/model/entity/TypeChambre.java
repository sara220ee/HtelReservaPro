package model.entity;

public enum TypeChambre {
    SINGLE, DOUBLE, SUITE;

    @Override
    public String toString() {
        switch (this) {
            case SINGLE:
                return "Chbre Simple";
            case DOUBLE:
                return "Chbre Double";
            case SUITE:
                return "Suite Luxe";
            default:
                return "Type inconnu";
        }
    }
}
