import { useState, useEffect } from 'react';

const useFetchData = (apiUrl) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  // const token = import.meta.env.VITE_TOKEN;

  useEffect(() => {
    const fetchData = async () => {
      try {

        var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6Im1hcmNlbG9sZWFsIiwiZXhwIjoxNzMzOTgzNDk0fQ.M-Fuv7FcJUMPbyXLQvnnogBUabsBOWU_utO14jVOrAY";
        console.log(token);

        const response = await fetch(apiUrl, {
          method: "GET",
          headers: {
            'Authorization': `Bearer ${token}`,
          },
          credentials: 'include',
        });

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
    };

    fetchData();
  }, [apiUrl]);

  return { data, loading, error };
};

export default useFetchData;
