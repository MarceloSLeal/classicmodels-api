import React, { useEffect, useState } from "react";

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
import useFetchData from "../../api/getData";

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
  officeId: yup.number().positive().required(),
});

const FormAddEmployee = () => {
  const url = Urls();
  const urlList = Urls();
  const urlIdNames = Urls();
  const jobTitleList = Constants().employees.jobTitle;

  // TODO achar uma forma de componentizar essas chamadas
  const [dataIdName, setDataIdName] = useState(null);
  const { data: dataId, loading: loadingId, error: errorId } = useFetchData(
    urlIdNames.employee.findByIdNames);
  useEffect(() => {
    if (dataId) {
      setDataIdName(dataId);
    }
  }, [dataId]);

  const [dataOfficeIdName, setDataOfficeIdName] = useState(null);
  const { data: dataNew, loading: loadingNew, error: errorNew } = useFetchData(
    urlList.offices.findByOfficeIds);
  useEffect(() => {
    if (dataNew) {
      setDataOfficeIdName(dataNew);
    }
  }, [dataNew]);

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');

  const handleFormSubmit = async (values) => {
    //adicionar codigo
    console.log(values);
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
                  {dataOfficeIdName && dataOfficeIdName.map((office) => (
                    <MenuItem key={office.id} value={office.id}>
                      {office.id} {office.city}
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

          </form>
        )}
      </Formik>

    </Box>
  )



}

export default FormAddEmployee;
