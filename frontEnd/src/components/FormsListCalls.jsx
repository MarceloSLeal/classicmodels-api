const FormListCalls = async (url, setData) => {
  try {
    const response = await fetch(url);
    const data = await response.json();
    setData(data);
  } catch (error) {
    console.error('Failed to fetch data:', error);
  }
}

export default FormListCalls;
