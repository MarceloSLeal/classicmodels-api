import React, { useEffect, useState } from "react";

import {
  Box, Button, Select, MenuItem, FormControl, InputLabel, Dialog, DialogActions,
  DialogContent, DialogContentText, DialogTitle,
} from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";

import { Formik } from "formik";
import * as yup from "yup";

import FormEmployeeAddInputs from "../../components/formAddInputs/Employees";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import useFetchData from "../../api/getData";

const initialValues = {
  lastName: "", firstName: "", email: "", reportsTo: "", jobTitle: "",
  extension: "", officeId: "",
};

const employeesSchema = yup.object().shape({
  lastName: yup.string().max(50).required(),
  firstName: yup.string().max(50).required(),
  email: yup.string().email().max(50).required(),
  reportsTo: yup.number().required(),
  jobTitle: yup.string().max(50),
  extension: yup.string().max(10).required(),
  officeId: yup.number().positive().required(),
});

const FormAddEmployee = () => {
  const url = Urls();
  const urlList = Urls();

  const [dataList, setDataList] = useState(null);
  const { data: dataNew, loading: loadingNew, error: errorNew } = useFetchData(urlList.employee.findByEmployeesIds);
  useEffect(() => {
    if (dataNew) {
      setDataList(dataNew);
    }
  }, [dataNew]);

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');

  const handleFormSubmit = () => {
    //adicionar codigo
  }

  return (
    <Box m="20px">
      <Header title="CREATE EMPLOYEE" subtitle="Create a new Employee" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={employeesSchema}
      >

        {({ values, errors, touched, handleBlur, handleChange, handleSubmit, setFieldValue }) => (
          <form onSubmit={handleSubmit}>
            <Box
              display="grid"
              gap="20px"
              gridTemplateColumns="repeat(4, minmax(0, 1fr))"
              sx={{
                "& > div": { gridColumn: isNonMobile ? undefined : "span 4" },
              }}
            >

              <FormEmployeeAddInputs handleBlur={handleBlur} handleChange={handleChange}
                values={values} touched={touched} errors={errors} />

            </Box>
          </form>
        )}
      </Formik>

    </Box>
  )



}

export default FormAddEmployee;
