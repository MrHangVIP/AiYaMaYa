package zsx.com.aiyamaya.listener;

public interface ErrorListener<T>{

    /**
     * Method to return the error that was generated. Will pass an error object with a helpful status code and error message.
     * 
     * @param error ServiceCommandError describing the error
     */
    public void onError(T error);
}
