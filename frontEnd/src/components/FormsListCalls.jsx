import { useEffect } from "react";
import useFetchData from "../api/getData";

// const FormListCalls = (url, setData) => {
//
//   const { data } = useFetchData(url);
//   useEffect(() => {
//     if (data) {
//       setData(data);
//     }
//   }, [data]);
//
// }

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
