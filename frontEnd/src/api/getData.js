import { useState, useEffect, useCallback } from 'react';

import { useRefreshToken } from "../auth/RefreshToken";

const useFetchData = (apiUrl) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const refreshToken = useRefreshToken();

  // const fetchData = async (retry = false) => {
  const fetchData = useCallback(async (retry = false) => {
    try {

      const response = await fetch(apiUrl, {
        method: "GET",
        credentials: 'include',
      });

      if (response.status === 403 && !retry) {
        console.warn("Token. Attemptin to refresh...");
        await refreshToken();
        return fetchData(true);
      }

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      setData(data);
    } catch (error) {
      setError(error);
    } finally {
      setLoading(false);
    }
    // };
  }, [apiUrl, refreshToken]);

  useEffect(() => {
    fetchData();
  }, [apiUrl]);

  // return { data, loading, error };
  return { data, loading, error, refetchData: fetchData };
};

export default useFetchData;
