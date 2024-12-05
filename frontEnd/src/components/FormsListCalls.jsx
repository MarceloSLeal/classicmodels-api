const FormListCalls = async (url, setData) => {
  const token = import.meta.env.VITE_TOKEN;

  console.log(token);

  try {
    const response = await fetch(url, {
      method: "GET",
      headers: {
        'Authorization': `${token}`,
      },
      credentials: 'include',
    });
    const data = await response.json();
    setData(data);
  } catch (error) {
    console.error('Failed to fetch data:', error);
  }
}

export default FormListCalls;
