import { TextField, FormControl, InputLabel, Select, MenuItem } from "@mui/material";

import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';

const OrdersFormInputs = ({ handleBlur, handleChange, values, touched,
  errors, setFieldValue, dataProductId, ordersSchema }) => {

  const tomorrow = dayjs().add(1, 'day');

  return (
    <>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DemoContainer
          components={['DatePicker']}
          sx={{ gridColumn: "span 2" }}
        >
          <DatePicker
            label="Required Date"
            defaultValue={tomorrow}
            disablePast
          />
        </DemoContainer>
      </LocalizationProvider>

      <TextField
        variant="filled"
        type="text"
        label="Comments"
        multiline
        rows={4}
        onBlur={handleBlur}
        onChange={handleChange}
        values={values.comments}
        name="comments"
        error={!!touched.comments && !!errors.comments}
        helperText={touched.comments && errors.comments}
        sx={{ gridColumn: "span 2" }}
      />

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 2" }}
      >
        <InputLabel id="productId-select-label">Product Id</InputLabel>
        <Select
          labelId="productId-select-label"
          id="productId-select"
          name="productId"
          // value={isReportsToValid ? values.reportsTo : ""}
          value={""}
          onChange={(event) => setFieldValue('productId',
            event.target.value)}
          onBlur={handleBlur}
          label="Product Id"
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          {/* {dataIdName && dataIdName.map((employee) => ( */}
          {/*   <MenuItem key={employee.id} value={employee.id}> */}
          {/*     {employee.id} {" "} */}
          {/*     {employee.lastName} {employee.firstName} - {employee.jobTitle} */}
          {/*   </MenuItem> */}
          {/* ))} */}
        </Select>
      </FormControl>

      <TextField
        variant="filled"
        type="text"
        label="Quantity"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.quantityOrdered}
        name="quantityOrdered"
        error={!!touched.quantityOrdered && !!errors.quantityOrdered}
        helperText={touched.quantityOrdered && errors.quantityOrdered}
        sx={{ gridColumn: "span 2" }}
      />

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 2" }}
      >
        <InputLabel id="customerId-select-label">Customer Id</InputLabel>
        <Select
          labelId="customerId-select-label"
          id="customerId-select"
          name="customerId"
          // value={isReportsToValid ? values.reportsTo : ""}
          value={""}
          onChange={(event) => setFieldValue('customerId',
            event.target.value)}
          onBlur={handleBlur}
          label="Customer Id"
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          {/* {dataIdName && dataIdName.map((employee) => ( */}
          {/*   <MenuItem key={employee.id} value={employee.id}> */}
          {/*     {employee.id} {" "} */}
          {/*     {employee.lastName} {employee.firstName} - {employee.jobTitle} */}
          {/*   </MenuItem> */}
          {/* ))} */}
        </Select>
      </FormControl>

    </>
  )
}

export default OrdersFormInputs;
