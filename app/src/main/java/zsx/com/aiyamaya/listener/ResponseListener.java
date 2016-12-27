package zsx.com.aiyamaya.listener;

public interface ResponseListener<T> {

    /**
     * Returns the success of the call of type T.
     * 
     * @param object Response object, can be any number of object types, depending on the protocol/capability/etc
     */
    abstract public void response(T object);
}
