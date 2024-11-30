import { useState, useEffect } from 'react';

const useFetchData = (apiUrl) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {

        // const response = await fetch(apiUrl);
        const response = await fetch(apiUrl, {
          method: "GET",
          headers: {
            'Authorization': 'HwuYQd-zrRw6WfCOUz4VcRrdjHeCGvyL6QD76aQUpdQ',
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
