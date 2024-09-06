import { TextField, FormControl, InputLabel, Select, MenuItem, FormHelperText } from "@mui/material";
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';
import Divider from '@mui/material/Divider';

const OrdersFormInputs = ({ handleBlur, handleChange, values, touched,
  errors, ordersSchema, setFieldValue, dataCustomersIdNameCreditLimit, }) => {

  return (
    <>
      <Divider textAlign="left" sx={{ gridColumn: "span 5" }}>
        Order Informations
      </Divider>

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 2" }}
        validationschema={ordersSchema}
        error={!!touched.customerId && !!errors.customerId}
      >
        <InputLabel id="customerId-select-label">Customer Id</InputLabel>
        <Select
          labelId="customerId-select-label"
          id="customerId-select"
          name="customerId"
          value={values.customerId}
          onChange={(event) => setFieldValue('customerId',
            event.target.value)}
          onBlur={handleBlur}
          label="Customer Id"
        >
          {dataCustomersIdNameCreditLimit && dataCustomersIdNameCreditLimit.map((customer) => (
            <MenuItem key={customer.id} value={customer.id}>
              {customer.id} {" "}
              {customer.name} {" CREDIT LIMIT "} {customer.creditLimit}
            </MenuItem>
          ))}
        </Select>
        <FormHelperText error={!!touched.customerId && !!errors.customerId}>
          {touched.customerId && errors.customerId}
        </FormHelperText>
      </FormControl>

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 1" }}
        validationschema={ordersSchema}
        error={!!touched.requiredDate?.date && !!errors.requiredDate?.date}
      >
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer
            components={['DatePicker']}
            sx={{ gridColumn: "span 1" }}
          >
            <DatePicker
              label="Required Date"
              disablePast
              value={values.requiredDate.date} // Objeto dayjs é esperado aqui
              onChange={(newValue) => {
                if (dayjs.isDayjs(newValue)) {
                  setFieldValue('requiredDate', { date: newValue });
                } else {
                  console.error("newValue is not a dayjs object", newValue);
                }
              }}
            />
          </DemoContainer>
        </LocalizationProvider>
        <FormHelperText error={!!touched.requiredDate?.date && !!errors.requiredDate?.date}>
          {touched.requiredDate?.date && errors.requiredDate?.date}
        </FormHelperText>
      </FormControl>

      <TextField
        variant="filled"
        type="text"
        label="Comments"
        multiline
        rows={3}
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.comments}
        name="comments"
        error={!!touched.comments && !!errors.comments}
        helperText={touched.comments && errors.comments}
        sx={{ gridColumn: "span 1" }}
      />

    </>
  )
}

export default OrdersFormInputs;
