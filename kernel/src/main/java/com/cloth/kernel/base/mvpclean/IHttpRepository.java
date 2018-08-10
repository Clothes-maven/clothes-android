package com.cloth.kernel.base.mvpclean;

public interface IHttpRepository {
    <T> T exec(final Class<T> service);
}
