import React, { useState } from "react";

import {
  Box, Button, Select, MenuItem, FormControl, FormHelperText, InputLabel,
  Dialog, DialogActions,
  DialogContent, DialogContentText, DialogTitle,
} from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";

import { Formik, Field } from "formik";
import * as yup from "yup";

import FormEmployeeAddInputs from "../../components/formAddInputs/Employees";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import { Constants } from "../../data/constants";
import FormListCalls from "../../components/FormsListCalls";

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
  const jobTitleList = Constants().employees.jobTitle;

  const [dataIdName, setDataIdName] = useState(null);
  FormListCalls(url.employee.findByIdNames, setDataIdName);

  const [dataOfficeIdName, setDataOfficeIdName] = useState(null);
  FormListCalls(url.offices.findByOfficeIds, setDataOfficeIdName);

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');

  const handleFormSubmit = async (values, { setSubmitting, resetForm }) => {

    setStatus('');
    setResponseCode(null);
    try {
      const response = await fetch(url.employee.findAll_Post, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus('Employee created successfully!');
        setResetFormFn(() => resetForm);
      } else {
        setStatus(`Error: ${data.title || 'Failed to create Employee'}`);
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
              gridTemplateColumns="repeat(4, minmax(0, 1fr))"
              sx={{
                "& > div": { gridColumn: isNonMobile ? undefined : "span 4" },
              }}
            >

              <FormEmployeeAddInputs
                handleBlur={handleBlur} handleChange={handleChange}
                values={values} touched={touched} errors={errors} />

              {/* TODO tentar colocar esses dois inputs no componente de inputs */}

              <FormControl
                variant="filled"
                sx={{ gridColumn: "span 2" }}
              >
                <InputLabel id="reportsTo-select-label">Reports To</InputLabel>
                <Select
                  labelId="reportsTo-select-label"
                  id="reportsTo-select-error"
                  name="reportsTo"
                  value={values.reportsTo}
                  onChange={(event) => setFieldValue('reportsTo',
                    event.target.value)}
                  onBlur={handleBlur}
                  label="Reports To"
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>
                  {dataIdName && dataIdName.map((employee) => (
                    <MenuItem key={employee.id} value={employee.id}>
                      {employee.id} {" "}
                      {employee.lastName} {employee.firstName} - {employee.jobTitle}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>

              <FormControl
                variant="filled"
                sx={{ gridColumn: "span 2" }}
                validationschema={employeesSchema}
                error={!!touched.officeId && !!errors.officeId}
              >
                <InputLabel id="office-select-label">Office Id</InputLabel>
                <Select
                  labelId="office-select-label"
                  id="office-select-error"
                  name="officeId"
                  value={values.officeId}
                  onChange={(event) => setFieldValue('officeId',
                    event.target.value)}
                  onBlur={handleBlur}
                  label="Office Id"
                >
                  {dataOfficeIdName && dataOfficeIdName.map((officeIdName) => (
                    <MenuItem key={officeIdName.id} value={officeIdName.id}>
                      {officeIdName.id} {officeIdName.city}
                    </MenuItem>
                  ))}
                </Select>
                <FormHelperText error={!!touched.officeId && !!errors.officeId}>
                  {touched.officeId && errors.officeId}</FormHelperText>
              </FormControl>

              <FormControl
                variant="filled"
                sx={{ gridColumn: "span 2" }}
                validationschema={employeesSchema}
                error={!!touched.jobTitle && !!errors.jobTitle}
              >
                <InputLabel id="job-select-label">Job Title</InputLabel>
                <Select
                  labelId="job-select-label"
                  id="job-select-error"
                  name="jobTitle"
                  value={values.jobTitle}
                  onChange={(event) => setFieldValue('jobTitle',
                    event.target.value)}
                  onBlur={handleBlur}
                  label="Job Title"
                >
                  {jobTitleList && jobTitleList.map((id) => (
                    <MenuItem key={id} value={id}>
                      {id}
                    </MenuItem>
                  ))}
                </Select>
                <FormHelperText error={!!touched.jobTitle && !!errors.jobTitle}>
                  {touched.jobTitle && errors.jobTitle}</FormHelperText>
              </FormControl>

            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Create New Employee
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
                  <Button onClick={handleClose} color="primary">
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

export default FormAddEmployee;
