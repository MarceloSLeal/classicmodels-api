import { useState, useEffect } from 'react';
import { useAuth } from '../auth/AuthContext';

const useFetchData = (apiUrl) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const userLogout = useAuth();

  useEffect(() => {
    const fetchData = async () => {
      try {

        // var token = localStorage.getItem('token');

        const response = await fetch(apiUrl, {
          method: "GET",
          // headers: {
          //   'Authorization': `Bearer ${token}`,
          // },
          credentials: 'include',
        });

        if (response.status === 403) {
          userLogout.logout();
          throw new Error('Token expired or access forbidden');
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
    };

    fetchData();
  }, [apiUrl]);

  return { data, loading, error };
};

export default useFetchData;
