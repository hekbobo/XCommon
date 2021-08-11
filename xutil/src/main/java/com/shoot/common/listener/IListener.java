

package com.shoot.common.listener;

public interface IListener<T> {
        public void onResponse(T response);

        public void onErrorResponse(int code, String error);
}
