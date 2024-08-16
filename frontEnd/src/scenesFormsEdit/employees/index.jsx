import { useLocation } from "react-router-dom";
import React, { useState } from "react";

import {
  Box, Button, Dialog, DialogActions, DialogContent, DialogContentText,
  DialogTitle,
  useMediaQuery
} from "@mui/material";

import { Formik } from "formik";
import * as yup from "yup";

import EmployeesFormInputs from "../../components/formInputs/Employees";
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
import { Constants } from "../../data/constants";

const employeeSchema = yup.object().shape({
  lastName: yup.string().max(50).required(),
  firstName: yup.string().max(50).required(),
  email: yup.string().email().max(50).required(),
  reportsTo: yup.number(),
  jobTitle: yup.string().max(50).required(),
  extension: yup.string().max(10).required(),
  officeId: yup.number().required(),
})

const FormEditEmployee = () => {
  const location = useLocation();
  const { rowData } = location.state || {};
  const url = Urls(rowData.id);
  const jobTitleList = Constants().employees.jobTitle;

  const [dataIdName, setDataIdName] = useState(null);
  FormListCalls(url.employees.findByIdNames, setDataIdName);

  const [dataOfficeIdName, setDataOfficeIdName] = useState(null);
  FormListCalls(url.offices.findByOfficeIds, setDataOfficeIdName);

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const navigate = useNavigate();

  const initialValues = {
    id: rowData.id, lastName: rowData.lastName, firstName: rowData.firstName,
    email: rowData.email, reportsTo: rowData.reportsTo, jobTitle:
      rowData.jobTitle, extension: rowData.extension, officeId: rowData.officeId
  };

  const handleFormSubmit = async (values, { setSubmitting }) => {
    setStatus('');
    setResponseCode(null);
    try {
      const response = await fetch(url.employees.findById_Put_Delete, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus('Employee updated successfully!');
      } else {
        setStatus(`Error: ${data.title || 'Failed to update Employee'}`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to update Employee'}`);
    }
    setSubmitting(false);
    setDialogOpen(true);
  }

  const handleClose = () => {
    setDialogOpen(false);
    if (responseCode === 200) {
      navigate("/employees");
    }
  }

  return (
    <Box m="20px">
      <Header title="EDIT EMPLOYEE" subtitle="Edit this Employee" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={employeeSchema}
      >
        {({ values, errors, touched, handleBlur, handleChange, handleSubmit,
          setFieldValue }) => (
          <form onSubmit={handleSubmit}>
            <Box
              display="grid"
              gap="20px"
              gridTemplateColumns="repeat(4, minmax(0, 1fr))"
              sx={{
                "& > div": { gridColumn: isNonMobile ? undefined : "span 4" },
              }}
            >

              <EmployeesFormInputs
                handleBlur={handleBlur} handleChange={handleChange}
                values={values} touched={touched} errors={errors} isEdit={true}
                dataIdName={dataIdName} dataOfficeIdName={dataOfficeIdName}
                jobTitleList={jobTitleList} setFieldValue={setFieldValue}
                employeeSchema={employeeSchema} />

            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Save
              </Button>
            </Box>

            <Dialog open={dialogOpen} onClose={handleClose}>
              <DialogTitle>Operation Status</DialogTitle>
              <DialogContent>
                <DialogContentText>
                  {status}
                  {responseCode !== null && <br />}
                  Response Code: {responseCode}
                </DialogContentText>
                <DialogActions>
                  <Button onClick={handleClose} color="inherit">
                    OK
                  </Button>
                </DialogActions>
              </DialogContent>
            </Dialog>

          </form>
        )}
      </Formik>
    </Box>
  )


}

export default FormEditEmployee
