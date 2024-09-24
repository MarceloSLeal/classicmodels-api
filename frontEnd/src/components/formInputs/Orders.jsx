import { TextField, FormControl, InputLabel, Select, MenuItem, FormHelperText } from "@mui/material";
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';

const OrdersFormInput = ({ handleBlur, handleChange, values, touched,
  errors, ordersSchema, setFieldValue, orderStatus }) => {

  return (
    <>

      <TextField
        disabled
        variant="filled"
        type="text"
        label="Id"
        value={values.id}
        name="id"
        sx={{ gridColumn: "span 1" }}
      />

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 1" }}
        validationschema={ordersSchema}
        error={false}
      >
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer
            components={['DatePicker']}
            sx={{ gridColumn: "span 1" }}
          >
            <DatePicker
              disabled
              label="Date"
              value={values.date}
            />
          </DemoContainer>
        </LocalizationProvider>
      </FormControl>

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 1" }}
        validationschema={ordersSchema}
        error={!!touched.shippedDate && !!errors.shippedDate}
      >
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer
            components={['DatePicker']}
            sx={{ gridColumn: "span 1" }}
          >
            <DatePicker
              label="Shipped Date"
              value={values.shippedDate}
              onChange={(newValue) => {
                if (newValue && dayjs.isDayjs(newValue)) {
                  setFieldValue('shippedDate', newValue);
                } else {
                  setFieldValue('shippedDate', null);
                }
              }}
            />
          </DemoContainer>
        </LocalizationProvider>
        <FormHelperText error={!!touched.shippedDate && !!errors.shippedDate}>
          {touched.shippedDate && errors.shippedDate}
        </FormHelperText>
      </FormControl>

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 1" }}
        validationschema={ordersSchema}
        error={false}
      >
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer
            components={['DatePicker']}
            sx={{ gridColumn: "span 1" }}
          >
            <DatePicker
              disabled
              label="Required Date"
              value={values.requiredDate}
            />
          </DemoContainer>
        </LocalizationProvider>
      </FormControl>

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 1" }}
        validationschema={ordersSchema}
        error={!!touched.status && !!errors.status}
      >
        <InputLabel id="order-status-label">Status</InputLabel>
        <Select
          labelId="order-status-label"
          id="order-status-label"
          name="status"
          value={values.status}
          onChange={(event) => setFieldValue('status',
            event.target.value)}
          onBlur={handleBlur}
          label="Status"
        >
          {orderStatus && orderStatus.map((id) => (
            <MenuItem key={id} value={id}>
              {id}
            </MenuItem>
          ))}
        </Select>
        <FormHelperText error={!!touched.status && !!errors.status}>
          {touched.status && errors.status}</FormHelperText>
      </FormControl>

      <TextField
        variant="filled"
        type="text"
        label="Comments"
        multiline
        rows={3}
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.comments || ""}
        name="comments"
        error={!!touched.comments && !!errors.comments}
        helperText={touched.comments && errors.comments}
        sx={{ gridColumn: "span 1" }}
      />

      <TextField
        disabled
        variant="filled"
        type="text"
        label="Customer Id"
        value={values.customerId}
        name="customerId"
        sx={{ gridColumn: "span 1" }}
      />

    </>
  )

}

export default OrdersFormInput;
