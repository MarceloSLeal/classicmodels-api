import React, { useEffect, useState } from "react";

import { Box, Button } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import EmployeesFormInputs from "../../components/formInputs/Employees";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import { Constants } from "../../data/constants";
import FormListCalls from "../../components/FormsListCalls";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"
import usePostForms from "../../components/formsRequests/PostForms";

const initialValues = {
  lastName: "", firstName: "", email: "", reportsTo: "", jobTitle: "",
  extension: "", officeId: "",
};

const employeesSchema = yup.object().shape({
  lastName: yup.string().max(50).required(),
  firstName: yup.string().max(50).required(),
  email: yup.string().email().max(50).required(),
  reportsTo: yup.number(),
  jobTitle: yup.string().max(50).required(),
  extension: yup.string().max(10).required(),
  officeId: yup.number().required(),
});

const FormAddEmployee = () => {
  const url = Urls();
  const { fetchPost } = usePostForms();
  const jobTitleList = Constants().employees.jobTitle;

  const [dataIdName, setDataIdName] = useState(null);
  const [dataOfficeIdName, setDataOfficeIdName] = useState(null);

  useEffect(() => {
    FormListCalls(url.employees.findByIdNames, setDataIdName);
    FormListCalls(url.offices.findByOfficeIds, setDataOfficeIdName);
  }, [])

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');

  const handleFormSubmit = async (values, { setSubmitting, resetForm }) => {
    setStatus('');
    setResponseCode(null);
    try {

      const response = await fetchPost(values, url.employees.findAll_Post);

      if (response.ok === false) {
        setStatus(`Error: ${response.status} - ${response.message}`);
        setDialogOpen(true);
        return;
      }

      setResponseCode(response.status);

      if (response.status === 201) {
        setStatus('Employee created successfully!');
        setResetFormFn(() => resetForm);
      } else {
        setStatus(`Error: ${response.title || 'Failed to create Employee'} - ${response.detail || ''}`);
      }

    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to create Employee'}`);
    }
    setSubmitting(false);
    setDialogOpen(true);
  }

  const handleClose = () => {
    setDialogOpen(false);
    if (responseCode === 201 && resetFormFn) {
      resetFormFn();
    }
  }

  return (
    <Box m="20px">
      <Header title="CREATE EMPLOYEE" subtitle="Create a new Employee" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={employeesSchema}
      >

        {({ values, errors, touched, handleBlur, handleChange, handleSubmit,
          setFieldValue }) => (
          <form onSubmit={handleSubmit}>
            <Box
              display="grid"
              gap="20px"
              gridTemplateColumns="repeat(5, minmax(0, 1fr))"
              sx={{
                "& > div": { gridColumn: isNonMobile ? undefined : "span 5" },
              }}
            >

              <Divider sx={{ gridColumn: "span 5" }} />

              <EmployeesFormInputs
                handleBlur={handleBlur} handleChange={handleChange}
                values={values} touched={touched} errors={errors} isEdit={false}
                dataIdName={dataIdName} dataOfficeIdName={dataOfficeIdName}
                jobTitleList={jobTitleList} setFieldValue={setFieldValue}
                employeesSchema={employeesSchema} />

            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Create New Employee
              </Button>
            </Box>

            <Divider sx={{ gridColumn: "span 5" }} />

          </form>
        )}
      </Formik>

      <OperationStatusDialog
        dialogOpen={dialogOpen} onClose={handleClose} status={status}
        responseCode={responseCode} onClick={handleClose}
      />

    </Box>
  )
}

export default FormAddEmployee;
