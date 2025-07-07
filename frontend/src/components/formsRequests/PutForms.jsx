// const PutForms = async (values, url) => {
//
//   const response = await fetch(url, {
//     method: 'PUT',
//     headers: {
//       'Content-Type': 'application/json',
//     },
//     credentials: 'include',
//
//     body: JSON.stringify(values),
//   })
//
//   return response;
// }
//
// export default PutForms;

import { useCallback } from 'react';
import { useRefreshToken } from "../../auth/RefreshToken";

const usePutForms = () => {
  const refreshToken = useRefreshToken();

  const fetchPut = useCallback(async (values, url, retry = false) => {
    try {
      
      const res = await fetch(url, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',

        body: JSON.stringify(values),
      });

      if (res.status === 403 && !retry) {
        console.warn("Token. Attempting to refresh...");
        await refreshToken();
        return fetchPut(values, url, true);
      }

      if (!res.ok) {
        throw new Error('Network response was not ok');
      }

      return res;

      } catch (error) {
        if (error instanceof TypeError) {
          return { ok: false, status: 'CONNECTION_REFUSED', message: "Can't connect to server" };
        }
      return { ok: false, status: 'UNKNOWN_ERROR', message: error.message };

    }
  }, [refreshToken]);

  return { fetchPut };
}

export default usePutForms;
