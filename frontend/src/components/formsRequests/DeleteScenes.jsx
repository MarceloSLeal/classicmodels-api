import { useCallback } from 'react';
import { useRefreshToken } from "../../auth/RefreshToken";

const useDeleteScenes = () => {
  const refreshToken = useRefreshToken();

  const fetchDelete = useCallback(async (url, retry = false) => {
    try {

      const res = await fetch(url, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
      })

      if (res.status === 403 && !retry) {
        console.warn("Token. Attempting to refresh...");
        await refreshToken();
        return fetchDelete(url, true);
      }

      if (!res.ok) {
        throw new Error('Network response was not ok');
      }

      return res;

    } catch (error) {
      if (error instanceof TypeError) {
        return { ok: false, status: 'CONNECTION_REFUSED'};
      }
      return { ok: false, status: 'UNKNOW_ERROR', message: error.message };
    }
  }, [refreshToken]);

  return { fetchDelete };
}

export default useDeleteScenes;
