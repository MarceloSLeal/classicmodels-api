import { useEffect } from "react";
import useFetchData from "../api/getData";

const FormListCalls = (url, setData) => {

  const { data } = useFetchData(url);
  useEffect(() => {
    if (data) {
      setData(data);
    }
  }, [data]);

}

export default FormListCalls;
