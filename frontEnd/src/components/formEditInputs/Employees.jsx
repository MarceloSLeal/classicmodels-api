import {
  TextField, FormControl, InputLabel, Select, MenuItem, FormHelperText
} from "@mui/material";

const EmployeesFormEditInputs = ({ handleBlur, handleChange, values, touched,
  errors, dataIdName, dataOfficeIdName, jobTitleList, setFieldValue,
  employeesSchema }) => {

  const isReportsToValid = dataIdName
    ? dataIdName.some((employeeReportsTo) => employeeReportsTo.id ===
      values.reportsTo) : false;

  const isOfficeIdValid = dataOfficeIdName
    ? dataOfficeIdName.some((employeeOfficeId) => employeeOfficeId.id ===
      values.officeId) : false;

  return (
    <>
      <TextField
        disabled
        variant="filled"
        type="text"
        label="Id"
        value={values.id}
        name="id"
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Last Name"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.lastName}
        name="lastName"
        error={!!touched.lastName && !!errors.lastName}
        helperText={touched.lastName && errors.lastName}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="First Name"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.firstName}
        name="firstName"
        error={!!touched.firstName && !!errors.firstName}
        helperText={touched.firstName && errors.firstName}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Email"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.email}
        name="email"
        error={!!touched.email && !!errors.email}
        helperText={touched.email && errors.email}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Extension"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.extension}
        name="extension"
        error={!!touched.extension && !!errors.extension}
        helperText={touched.extension && errors.extension}
        sx={{ gridColumn: "span 2" }}
      />

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 2" }}
      >
        <InputLabel id="reportsTo-select-label">Reports To</InputLabel>
        <Select
          labelId="reportsTo-select-label"
          id="reportsTo-select-error"
          name="reportsTo"
          //value={values.reportsTo}
          value={isReportsToValid ? values.reportsTo : ""}
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
          //value={values.officeId}
          value={isOfficeIdValid ? values.officeId : ""}
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
    </>
  );
}

export default EmployeesFormEditInputs;
