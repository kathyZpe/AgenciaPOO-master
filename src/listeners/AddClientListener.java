package listeners;

public interface AddClientListener {
    void OnCancel();

    void OnAdd(String name, String surname, String model, String registration, String phone, String email);
}
