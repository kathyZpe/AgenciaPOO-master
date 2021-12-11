package listeners;

/*
* Interface con el proposito de ser usado como listener
* */

public interface SQLListener {
    void onSQLException(String msg); // En caso de una exception relacionado con SQL
}
