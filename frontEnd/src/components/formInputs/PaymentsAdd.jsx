import { TextField, FormControl, InputLabel, Select, MenuItem, FormHelperText } from "@mui/material";
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';
import Divider from '@mui/material/Divider';

const PaymentsAddFormInputs = ({ handleBlur, handleChange, values, touched,
  errors, paymentsSchema, setFieldValue, dataOrdersIdStatus, handleSelectOption }) => {

  return (
    <>
      <Divider textAlign="left" sx={{ gridColumn: "span 5" }}>
        Payment Information
      </Divider>

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 2" }}
        validationschema={paymentsSchema}
        error={!!touched.customerId && !!errors.customerId}
      >
        <InputLabel id="orderId-select-label">Order Id</InputLabel>
        <Select
          labelId="orderId-select-label"
          id="orderId-select"
          name="orderId"
          value={values.orderId}
          onChange={(event) => {
            const selectedValue = event.target.value;
            setFieldValue('orderId', selectedValue);
            handleSelectOption(selectedValue);
          }}
          onBlur={handleBlur}
          label="Order Id"
        >
          {dataOrdersIdStatus && dataOrdersIdStatus.map((order) => (
            <MenuItem key={order.id} value={order.id}>
              {order.id} {" "}
              {" - STATUS - "} {order.status}
            </MenuItem>
          ))}
        </Select>
        <FormHelperText error={!!touched.orderId && !!errors.orderId}>
          {touched.orderId && errors.orderId}
        </FormHelperText>
      </FormControl>

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 1" }}
        validationschema={paymentsSchema}
        error={!!touched.paymentDate && !!errors.paymentDate}
      >
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer
            components={['DatePicker']}
            sx={{ gridColumn: "span 1" }}
          >
            <DatePicker
              label="Payment Date"
              disablePast
              value={values.paymentDate}
              onChange={(newValue) => {
                if (dayjs.isDayjs(newValue)) {
                  setFieldValue('paymentDate', newValue);
                } else {
                  setFieldValue('paymentDate', null);
                }
              }}
              format="DD/MM/YYYY"
            />
          </DemoContainer>
        </LocalizationProvider>
        <FormHelperText error={!!touched.paymentDate && !!errors.paymentDate}>
          {touched.paymentDate && errors.paymentDate}
        </FormHelperText>
      </FormControl>

      <TextField
        disabled
        variant="filled"
        type="text"
        label="Amount"
        onChange={(event) => setFieldValue('amount',
          event.target.value)}
        value={values.amount}
        name="amount"
        sx={{ gridColumn: "span 1" }}
      />

    </>
  )

}

export default PaymentsAddFormInputs;
