import { TextField } from "@mui/material";

const FormEmployeeAddInputs = ({ handleBlur, handleChange, values, touched,
  errors }) => {

  return (
    <>
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
    </>
  );
}

export default FormEmployeeAddInputs;
